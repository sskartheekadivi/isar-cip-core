#!/bin/sh
#
# Merge an upstream release tag into the TI development branch.
#
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#
# Usage: merge-upstream-tag.sh <tag>
#
# Environment:
#   UPSTREAM_REMOTE  remote of upstream isar-cip-core (default: upstream)
#   TI_BRANCH        branch to merge into (default: ti-next)

set -e

UPSTREAM_REMOTE=${UPSTREAM_REMOTE:-upstream}
TI_BRANCH=${TI_BRANCH:-ti-next}

if [ $# -ne 1 ]; then
	echo "Usage: $0 <tag>" >&2
	exit 1
fi
TAG=$1

if ! git remote get-url "$UPSTREAM_REMOTE" >/dev/null 2>&1; then
	echo "Remote '$UPSTREAM_REMOTE' does not exist, add it with:" >&2
	echo "  git remote add $UPSTREAM_REMOTE https://gitlab.com/cip-project/cip-core/isar-cip-core.git" >&2
	exit 1
fi

if [ -n "$(git status --porcelain --untracked-files=no)" ]; then
	echo "Working tree is not clean" >&2
	exit 1
fi

git config rerere.enabled true

echo "Fetching tags from $UPSTREAM_REMOTE..."
git fetch --no-tags "$UPSTREAM_REMOTE" "+refs/tags/$TAG:refs/tags/$TAG"

if git merge-base --is-ancestor "refs/tags/$TAG" "$TI_BRANCH"; then
	echo "$TAG is already merged into $TI_BRANCH"
	exit 0
fi

git checkout "$TI_BRANCH"
git merge --no-ff -m "Merge tag '$TAG' into $TI_BRANCH" "refs/tags/$TAG" || {
	echo >&2
	echo "Resolve the conflicts, then run 'git commit' to finish the merge." >&2
	exit 1
}

echo
echo "Merged $TAG into $TI_BRANCH. Review it, build the TI boards with the CIP"
echo "and the TI CIP kernel, then push $TI_BRANCH."

# TI fork of isar-cip-core

This fork of [isar-cip-core](https://gitlab.com/cip-project/cip-core/isar-cip-core)
adds support for Texas Instruments boards that are not CIP reference
platforms and lets you build them with the TI CIP kernel. Everything else is
upstream, see `README.md` for the general usage.

## Branches

| Branch      | Content |
|-------------|---------|
| `ti-master` | An upstream release tag, unchanged. Promoted from `ti-next` by CI. |
| `ti-next`   | The upstream release tag plus the TI changes. All development happens here. |

### Keeping up with upstream

For every new upstream release tag, merge the tag into `ti-next`:

    git remote add upstream https://gitlab.com/cip-project/cip-core/isar-cip-core.git
    scripts/merge-upstream-tag.sh v1.14

This is a plain `git merge --no-ff <tag>`, no rebase, so the published history
is never rewritten. Enable `git config rerere.enabled true` to let git
remember how conflicts were resolved.

The TI changes are kept in new files as much as possible. Only `Kconfig` is
modified in a way that may conflict. After a merge, check that:

 - the TI boards still build with the CIP kernel and with the TI CIP kernel
 - U-Boot, TF-A and OP-TEE recipes of the TI boards still match upstream's
   structure, as they are derived from the AM62Px SK ones

## Supported TI boards

| Board          | Machine         | Kernel |
|----------------|-----------------|--------|
| AM62x SK       | `ti-am62x-sk`   | 6.12.x |
| AM62Px SK      | `ti-am62px-sk`  | 6.12.x (upstream) |
| AM64x SK       | `ti-am64x-sk`   | 6.12.x |

All of them boot from U-Boot (R5 SPL, TF-A, OP-TEE) on HS-FS devices. The boot
firmware comes from `ti-linux-firmware`, whose release is set by
`TI_FIRMWARE_PV` in the `u-boot-ti-*` recipes.

## Choosing the kernel

The CIP kernel is the default. For the TI boards with kernel 6.12.x, the image
configuration menu offers a "Kernel source" choice:

    ./kas-container menu

Selecting "TI CIP kernel" adds `kas/opt/kernel-source-ti-cip.yml`, e.g.:

    ./kas-container build kas-cip.yml:kas/board/ti-am62x-sk.yml:kas/opt/6.12.yml:kas/opt/kernel-source-ti-cip.yml:kas/opt/ftpm-stmm.yml:kas/opt/trixie.yml

The TI CIP kernel is not available together with the real-time kernel.

### How the TI CIP kernel is built

The sources are taken from `github.com/sskartheekadivi/linux`. The kernel is
called `ti-cip`, so the packages are `linux-image-ti-cip`, `linux-headers-ti-cip`
and so on. In contrast to the CIP kernel:

 - the config does not come from `cip-kernel-config`, but from the kernel tree:
   `arch/arm64/configs/defconfig`, pruned with
   `kernel/configs/ti_arm64_prune.config`
 - `recipes-kernel/linux/files/ti-cip-core.cfg` enables options that CIP Core
   features need but the TI defconfig lacks (fTPM and StMM via OP-TEE, disk
   encryption, dm-verity, EROFS)
 - the release is part of the kernel release (`uname -r`), e.g.
   `6.12.57-ti-11.02.20`

The CIP kernel recipes and `cip-kernel-config` are not touched, so builds
with the CIP kernel behave exactly as upstream.

### Recipe naming

Release recipes are named after the kernel version, the CIP release if the
kernel contains one, and the tag of the TI CIP kernel:

    linux-ti-cip_<kernel version>[-cip<N>]-ti-<tag>.bb

e.g. `linux-ti-cip_6.12.57-ti-11.02.20.bb` or
`linux-ti-cip_6.12.111-cip32-ti-11.02.22.bb`. Everything after `-ti-` is the
git tag that gets fetched, so it has to match the tag exactly.

To add a release:

 1. Copy the latest `linux-ti-cip_*.bb`, rename it for the new tag.
 2. Update `SRC_URI[sha256sum]` with the sum of
    `https://github.com/sskartheekadivi/linux/archive/refs/tags/<tag>.tar.gz`.
 3. Verify the config: all options in `ti-cip-core.cfg` must still end up
    enabled in the resulting `.config`.

### Head of the TI CIP kernel

With "Head of selected CIP kernel branch" selected, the head recipe
`linux-ti-cip_6.12-ti-cip-head.bb` builds the head of the branch
`ti-linux-6.12.y-cip`. To build another branch, e.g. the not yet promoted
`ti-linux-6.12.y-cip-cicd`, set `TI_CIP_BRANCH` in the local configuration.

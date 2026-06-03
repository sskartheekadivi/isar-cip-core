#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2024
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

inherit initramfs-hook

MAINTAINER = "cip-dev <cip-dev@lists.cip-project.org>"
DESCRIPTION = "Initramfs hook that adds SquashFS filesystem support during early boot"

HOOK_ADD_MODULES = "squashfs"

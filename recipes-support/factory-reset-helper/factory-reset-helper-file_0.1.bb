#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT

require recipes-support/factory-reset-helper/factory-reset-helper.inc

DEBIAN_CONFLICTS = "factory-reset-helper-efi"

FACTORY_RESET_MARKER ?= ".factory-reset"
FACTORY_RESET_MARKER_STORAGE_DEVICE ?= "/dev/disk/by-partlabel/var"
FACTORY_RESET_TYPE = "file"

DEBIAN_DEPENDS .= ", coreutils, util-linux, e2fsprogs, bsdextrautils"

python do_check_compatibility() {
    if "encrypt-partitions" in d.getVar('OVERRIDES'):
        bb.error("This recipe is incompatible with encrypt-partitions")
}
addtask check_compatibility after do_fetch before do_install

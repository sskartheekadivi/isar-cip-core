#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT

require recipes-initramfs/initramfs-hook/hook.inc
DESCRIPTION = "Delete the content of the given Devices"

# list of partitions by label
INITRAMFS_FACTORY_RESET_DEVICES ??= "/dev/disk/by-partlabel/var"
INITRAMFS_FACTORY_RESET_LUKS_FORMAT_TYPE ??= "ext4"
INITRAMFS_FACTORY_RESET_CLEAR_TPM ??= '1'
SRC_URI += " \
    file://reset-env.tmpl \
    file://hook"

SRC_URI += "${@ 'file://local-top' if bb.utils.to_boolean(d.getVar('INITRAMFS_FACTORY_RESET_CLEAR_TPM')) else 'file://local-bottom'}"

TEMPLATE_FILES += "reset-env.tmpl"
TEMPLATE_VARS += " INITRAMFS_FACTORY_RESET_DEVICES \
                   INITRAMFS_FACTORY_RESET_LUKS_FORMAT_TYPE"

RDEPENDS = "factory-reset-helper"
DEBIAN_DEPENDS .= ", coreutils, util-linux, e2fsprogs, btrfs-progs, awk, factory-reset-helper, findutils"
DEBIAN_DEPENDS:append:encrypt-partitions = ", tpm2-tools"
HOOK_COPY_EXECS = "mountpoint findmnt mktemp rmdir basename \
                   mke2fs mkfs.btrfs awk blkid rm get-factory-reset.sh \
                   chattr grep find"
HOOK_COPY_EXECS:append:encrypt-partitions = " tpm2_clear"

do_install[cleandirs] += "${D}/usr/share/factory-reset/"
do_install:prepend() {
    install -m 0755 "${WORKDIR}/reset-env" \
        "${D}/usr/share/factory-reset/reset-env"
}

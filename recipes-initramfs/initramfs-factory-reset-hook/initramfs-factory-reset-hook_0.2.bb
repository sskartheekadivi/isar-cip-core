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
INITRAMFS_FACTORY_RESET_CLEAR_TPM ??= '0'
INITRAMFS_FACTORY_RESET_CLEAR_TPM:encrypt-partitions ?= '1'

SRC_URI += " \
    file://factory-reset-script.tmpl \
    file://reset-env.tmpl \
    file://hook"

FACTORY_RESET_HOOK_TARGET = "${@ 'local-top' if bb.utils.to_boolean(d.getVar('INITRAMFS_FACTORY_RESET_CLEAR_TPM')) else 'local-bottom'}"

TEMPLATE_FILES += "reset-env.tmpl factory-reset-script.tmpl"
TEMPLATE_VARS += " INITRAMFS_FACTORY_RESET_DEVICES \
                   INITRAMFS_FACTORY_RESET_LUKS_FORMAT_TYPE \
                   INITRAMFS_FACTORY_RESET_CLEAR_TPM"

RDEPENDS = "factory-reset-helper"
DEBIAN_DEPENDS .= ", coreutils, util-linux, e2fsprogs, btrfs-progs, awk, factory-reset-helper, findutils"
DEBIAN_DEPENDS:append:encrypt-partitions = ", tpm2-tools"
HOOK_COPY_EXECS = "mountpoint findmnt mktemp rmdir basename \
                   mke2fs mkfs.btrfs awk blkid rm get-factory-reset.sh \
                   chattr grep find"
HOOK_COPY_EXECS:append:encrypt-partitions = " tpm2_clear"

# provide the script under the required name
do_prepare_build:append() {
    rm -f ${WORKDIR}/local-top ${WORKDIR}/local-bottom
    ln -sf ${WORKDIR}/factory-reset-script ${WORKDIR}/${FACTORY_RESET_HOOK_TARGET}
}

do_install[cleandirs] += "${D}/usr/share/factory-reset/"
do_install:prepend() {
    install -m 0755 "${WORKDIR}/reset-env" \
        "${D}/usr/share/factory-reset/reset-env"
}

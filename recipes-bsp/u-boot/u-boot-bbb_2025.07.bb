#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2022-2025
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

require u-boot-common-${PV}.inc

SRC_URI += "file://no-grub-workaround.cfg"

COMPATIBLE_MACHINE = "^(bbb)$"

U_BOOT_CONFIG = "am335x_evm_defconfig"
U_BOOT_BIN = "all"

do_prepare_build:append() {
    sed -ni '/### Disable grub workaround/q;p' ${S}/configs/${U_BOOT_CONFIG}
    cat ${WORKDIR}/no-grub-workaround.cfg >> ${S}/configs/${U_BOOT_CONFIG}

    echo "MLO u-boot.img /usr/lib/u-boot/${MACHINE}" > \
        ${S}/debian/u-boot-${MACHINE}.install
}

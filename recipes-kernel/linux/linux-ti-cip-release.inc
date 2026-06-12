#
# CIP Core, generic profile
#
# Copyright (c) Siemens, 2022-2025
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require linux-ti-cip-common.inc

KERNEL_MAJOR_MINOR = "${@d.getVar('PV').split('.')[0] + '.' + d.getVar('PV').split('.')[1]}"

SRC_URI += " \
    https://github.com/sskartheekadivi/linux/archive/refs/tags/v${PV}.tar.gz \
    "

S = "${WORKDIR}/linux-${PV}"

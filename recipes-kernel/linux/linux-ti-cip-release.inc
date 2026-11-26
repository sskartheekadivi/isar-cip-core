#
# CIP Core, generic profile
#
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require linux-ti-cip-common.inc

# PV is <kernel version>-ti-<tag of the TI CIP kernel>
TI_CIP_TAG = "${@d.getVar('PV').split('-ti-')[1]}"

# The kernel tree does not say which release it is. Add it to the kernel
# release, so that it shows up in uname -r and the modules directory.
LINUX_VERSION_EXTENSION = "-ti-${TI_CIP_TAG}"

SRC_URI += " \
    https://github.com/sskartheekadivi/linux/archive/refs/tags/${TI_CIP_TAG}.tar.gz;downloadfilename=linux-ti-cip-${TI_CIP_TAG}.tar.gz \
    "

S = "${WORKDIR}/linux-${TI_CIP_TAG}"

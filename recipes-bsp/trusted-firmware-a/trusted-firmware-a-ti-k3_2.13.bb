#
# Copyright (c) Texas Instruments Incorporated, 2025
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require recipes-bsp/trusted-firmware-a/trusted-firmware-a-custom.inc

SRC_URI += "https://github.com/ARM-software/arm-trusted-firmware/archive/v${PV}.tar.gz;downloadfilename=arm-trusted-firmware-${PV}.tar.gz"
SRC_URI[sha256sum] = "68333c8be57830e7430d7dd39064826729923d86d6bb7af38d713d4e9d15dcf9"

S = "${WORKDIR}/arm-trusted-firmware-${PV}"

TF_A_NAME = "ti-k3"
TF_A_PLATFORM = "k3"
TF_A_EXTRA_BUILDARGS = "CFG_ARM64=y TARGET_BOARD=lite SPD=opteed K3_PM_SYSTEM_SUSPEND=1"
TF_A_BINARIES = "lite/release/bl31.bin"

COMPATIBLE_MACHINE = "ti-am62px-sk"

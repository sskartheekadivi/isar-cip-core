#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2022-2026
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

SRC_URI:append = " ${@ \
    'git://gitlab.com/cip-project/cip-kernel/cip-kernel-config.git;protocol=https;branch=master;destsuffix=cip-kernel-config;name=cip-kernel-config \
    file://no-tpm-hwrng.cfg' \
    if bb.utils.to_boolean(d.getVar('USE_CIP_KERNEL_CONFIG')) else '' \
    }"

SRCREV_cip-kernel-config ?= "a292d21b5ef582b2e42be0f0404eb6fb19f0786f"

do_fetch[vardeps] += "SRCREV_cip-kernel-config"

KERNEL_DEFCONFIG_VERSION ?= "${KERNEL_MAJOR_MINOR}.y-cip"
KERNEL_DEFCONFIG ?= "${@ \
    'cip-kernel-config/${KERNEL_DEFCONFIG_VERSION}/${KERNEL_ARCH}/cip_merged_defconfig' \
    if bb.utils.to_boolean(d.getVar('USE_CIP_KERNEL_CONFIG')) else \
    '${MACHINE}_defconfig'}"

KBUILD_DEPENDS:append = "${@ \
    ', lzop, lz4' \
    if bb.utils.to_boolean(d.getVar('USE_CIP_KERNEL_CONFIG')) else ''}"

SRC_URI:append = " ${@ \
    'file://no-module-signing.cfg' if bb.utils.to_boolean(d.getVar('NO_MODULE_SIGNING')) else '' \
    }"

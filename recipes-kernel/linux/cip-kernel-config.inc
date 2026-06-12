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
    'git://github.com/sskartheekadivi/cip-kernel-config.git;protocol=https;branch=ti-master;destsuffix=cip-kernel-config;name=cip-kernel-config \
    file://no-tpm-hwrng.cfg' \
    if d.getVar('KERNEL_NAME') == 'ti-cip' and bb.utils.to_boolean(d.getVar('USE_CIP_KERNEL_CONFIG')) else \
    'git://gitlab.com/cip-project/cip-kernel/cip-kernel-config.git;protocol=https;branch=master;destsuffix=cip-kernel-config;name=cip-kernel-config \
    file://no-tpm-hwrng.cfg' \
    if bb.utils.to_boolean(d.getVar('USE_CIP_KERNEL_CONFIG')) else '' \
    }"

SRCREV_cip-kernel-config ?= "${@ \
    'a292d21b5ef582b2e42be0f0404eb6fb19f0786f' \
    if d.getVar('KERNEL_NAME') == 'ti-cip' else \
    '7742c0f098a3bae264dcb7db8128e99954249bb8' \
    }"

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

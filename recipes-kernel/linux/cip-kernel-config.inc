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

SRC_URI:append = " ${@ \
    'git://gitlab.com/sskartheekadivi/cip-kernel-config.git;protocol=https;branch=ftpm-debug;destsuffix=cip-kernel-config;name=cip-kernel-config' \
    if d.getVar('USE_CIP_KERNEL_CONFIG') == '1' else '' \
    }"

# 'git://gitlab.com/cip-project/cip-kernel/cip-kernel-config.git;protocol=https;branch=master;destsuffix=cip-kernel-config;name=cip-kernel-config'

SRCREV_cip-kernel-config ?= "69a9ecd633aa386986358d7182352fb7d33c09eb"

do_fetch[vardeps] += "SRCREV_cip-kernel-config"

KBUILD_DEPENDS:append = "${@', lzop' if d.getVar('USE_CIP_KERNEL_CONFIG') == '1' else ''}"

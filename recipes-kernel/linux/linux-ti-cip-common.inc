#
# CIP Core, generic profile
#
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

FILESEXTRAPATHS:prepend := "${FILE_DIRNAME}/files:"

inherit linux-kernel

MAINTAINER = "cip-dev <cip-dev@lists.cip-project.org>"

require recipes-kernel/linux/cip-kernel-config.inc

SRC_URI += "${@'file://preempt-rt.cfg' if d.getVar('KERNEL_NAME') == 'cip-rt' else ''}"

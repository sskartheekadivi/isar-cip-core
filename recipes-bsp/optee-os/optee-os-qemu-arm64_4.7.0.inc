#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

require optee-os-qemu.inc

SRC_URI += "file://stmm.patch"
SRC_URI[sha256sum] = "976b9c184678516038d4e79766608e81d10bf136f76fd0db2dc48f90f994fbd9"

OPTEE_NAME = "qemu-arm64"
OPTEE_PLATFORM = "vexpress-qemu_armv8a"
OPTEE_EXTRA_BUILDARGS += "CFG_ARM64_core=y CFG_USER_TA_TARGETS=ta_arm64"

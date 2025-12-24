#
# CIP Core, generic profile
#
# Copyright (c) Siemens, 2026
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

require u-boot-common-${PV}.inc

COMPATIBLE_MACHINE = "^(beaglev-fire)$"

SRC_URI += "file://beaglev-fire-config.yaml;subdir=debian"

DEPENDS += "hss-payload-generator-native"
DEBIAN_BUILD_DEPENDS += ", hss-payload-generator:native"

U_BOOT_CONFIG = "beaglev_fire_defconfig"
U_BOOT_BIN = "u-boot.bin"
U_BOOT_EXTRA_BUILDCMD = "hss-payload-generator -c debian/beaglev-fire-config.yaml payload.bin"

#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT
#

inherit dpkg-raw

DPKG_ARCH = "all"
DESCRIPTION = "helper functions for cip initramfs-tools extensions"

SRC_URI += "file://cip-initramfs-functions"

do_install[cleandirs] += "${D}/usr/share/initramfs-tools/scripts/"
do_install() {
    install -m 755 ${WORKDIR}/cip-initramfs-functions ${D}/usr/share/initramfs-tools/scripts/cip-initramfs-functions
}

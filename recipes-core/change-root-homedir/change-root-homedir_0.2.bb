#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2021-2025
#
# Authors:
#  Henning Schild <henning.schild@siemens.com>
#
# SPDX-License-Identifier: MIT

inherit dpkg-raw

DESCRIPTION = "Move roots homedir to /home for read-only rootfs"

SRC_URI = "file://postinst"

do_prepare_build:append:separate-home-part() {
    echo "d /home/root 0700 root root -" > ${S}/debian/${BPN}.tmpfiles
}

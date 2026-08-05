#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2021
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT

inherit dpkg-raw

MAINTAINER = "cip-dev <cip-dev@lists.cip-project.org>"
DESCRIPTION = "systemd unit to mount /tmp as tmpfs"

SRC_URI = "file://postinst"

do_install() {
}

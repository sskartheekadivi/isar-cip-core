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

SRC_URI = "file://postinst \
           file://home.mount"

do_install[cleandirs]+="${D}/lib/systemd/system"
do_install() {
    install -m 0644 ${WORKDIR}/home.mount ${D}/lib/systemd/system/home.mount

}

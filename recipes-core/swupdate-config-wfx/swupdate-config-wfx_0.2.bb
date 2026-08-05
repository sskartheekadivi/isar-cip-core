#
# CIP Core, generic profile
#
# Copyright (c) Toshiba Corporation, 2026
#
# Authors:
#  Adithya Balakumar <adithya.balakumar@toshiba-tsip.com>
#
# SPDX-License-Identifier: MIT
#

inherit dpkg-raw

MAINTAINER = "cip-dev <cip-dev@lists.cip-project.org>"
DESCRIPTION = "SWUpdate Suricatta WFX client configuration"

PROVIDES := "${PN}"
DEBIAN_PROVIDES := "${PN}"

PN .= "-${MACHINE}"

WFX_DEVICE_ID ?= "$(cat /etc/machine-id)"

SRC_URI += "file://suricatta_wfx.conf.tmpl"

TEMPLATE_FILES += " suricatta_wfx.conf.tmpl"
TEMPLATE_VARS += " WFX_URL WFX_DEVICE_ID"

do_install() {
    install -d ${D}/etc/swupdate/conf.d
    install -m 0644 ${WORKDIR}/suricatta_wfx.conf ${D}/etc/swupdate/conf.d/suricatta_wfx.conf
}

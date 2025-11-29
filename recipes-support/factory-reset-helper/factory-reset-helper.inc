#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT

inherit dpkg-raw

DESCRIPTION = "helper script to execute a factory reset"

PROVIDES = "factory-reset-helper"
DEBIAN_PROVIDES = "factory-reset-helper"

SRC_URI = "file://set-factory-reset-${FACTORY_RESET_TYPE}.sh.tmpl \
           file://get-factory-reset-${FACTORY_RESET_TYPE}.sh.tmpl"

TEMPLATE_FILES += "set-factory-reset-${FACTORY_RESET_TYPE}.sh.tmpl \
                   get-factory-reset-${FACTORY_RESET_TYPE}.sh.tmpl"

TEMPLATE_VARS += " FACTORY_RESET_MARKER \
                   FACTORY_RESET_EFIVARS_GUID \
                   FACTORY_RESET_MARKER_STORAGE_DEVICE"

do_install[cleandirs] += "${D}/usr/sbin/"
do_install:prepend() {
    install -m 0700 "${WORKDIR}/set-factory-reset-${FACTORY_RESET_TYPE}.sh" \
        "${D}/usr/sbin/set-factory-reset.sh"
    install -m 0700 "${WORKDIR}/get-factory-reset-${FACTORY_RESET_TYPE}.sh" \
        "${D}/usr/sbin/get-factory-reset.sh"
}

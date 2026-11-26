#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2022-2026
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require linux-ti-cip-common.inc

def get_commit(d):
    try:
        return bb.fetch2.get_srcrev(d).replace('AUTOINC+', '')
    except (bb.fetch2.FetchError, bb.fetch2.NetworkAccess):
        return ""

KERNEL_MAJOR_MINOR := "${@d.getVar('PV').split('-')[0]}"

# Releases are tagged on this branch. Use ti-linux-<version>.y-cip-cicd to
# build the not yet promoted state.
TI_CIP_BRANCH ?= "ti-linux-${KERNEL_MAJOR_MINOR}.y-cip"

PV .= "${@'-g' + get_commit(d)}"

SRC_URI += "git://github.com/sskartheekadivi/linux.git;protocol=https;branch=${TI_CIP_BRANCH};name=ti-cip-kernel"
SRCREV_FORMAT = "ti-cip-kernel"
SRCREV_ti-cip-kernel = "${AUTOREV}"

S = "${WORKDIR}/git"

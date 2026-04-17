#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2020-2026
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT
#

DESCRIPTION = "efibootguard boot loader"
DESCRIPTION_DEV = "efibootguard development library"
HOMEPAGE = "https://github.com/siemens/efibootguard"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${LAYERDIR_isar}/licenses/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
MAINTAINER = "Jan Kiszka <jan.kiszka@siemens.com>"

EBG_VERSION = "${@d.getVar('PV').split('-')[0]}"

SRC_URI = " \
    https://github.com/siemens/efibootguard/archive/refs/tags/v${EBG_VERSION}.tar.gz;downloadfilename=efibootguard_${EBG_VERSION}.orig.tar.gz;unpack=0;name=tarball \
    git://salsa.debian.org/debian/efibootguard.git;protocol=https;branch=master;name=debian \
    file://debian-patches/0001-d-control-Make-compatible-with-debian-buster.patch \
    file://debian-patches/0002-Revert-d-Enable-unit-tests-post-trixie.patch \
    "
SRC_URI[tarball.sha256sum] = "6c68c541311dcc8c2be0042f7887acc2a24d2ac68a88d426380fdbda9f476ae8"
SRCREV_debian = "5d27085b1fcacce130deecc5347792437acbeda6"

PROVIDES = "libebgenv-dev libebgenv0 efibootguard efibootguard-tools"

S = "${WORKDIR}/git"

PATCHTOOL = "git"

inherit dpkg

DEPENDS:buster   = "python-shtab"
DEPENDS:bullseye = "python-shtab"
DEPENDS:bookworm = "python-shtab"

# needed for buster, bullseye could use compat >= 13
python() {
    arch = d.getVar('DISTRO_ARCH')
    cmd = 'dpkg-architecture -a {} -q DEB_HOST_MULTIARCH'.format(arch)
    with os.popen(cmd) as proc:
        d.setVar('DEB_HOST_MULTIARCH', proc.read())
}

do_prepare_build() {
    deb_add_changelog
}

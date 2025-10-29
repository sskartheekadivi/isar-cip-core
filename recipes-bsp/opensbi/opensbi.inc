#
# Copyright (c) Siemens AG, 2023-2025
#
# SPDX-License-Identifier: MIT

inherit dpkg

SRC_URI = " \
    https://github.com/riscv/opensbi/archive/v${PV}.tar.gz;downloadfilename=opensbi-${PV}.tar.gz;name=opensbi"

S = "${WORKDIR}/opensbi-${PV}"

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build() {
    cp ${WORKDIR}/${MACHINE}-rules ${WORKDIR}/rules
    deb_debianize

    echo "build/platform/generic/firmware/${OPENSBI_BIN} /usr/lib/opensbi/${MACHINE}/" > ${S}/debian/install
}

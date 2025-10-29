#
# Copyright (c) Siemens AG, 2023-2025
#
# SPDX-License-Identifier: MIT

inherit opensbi

SRC_URI += "https://github.com/riscv/opensbi/archive/v${PV}.tar.gz;downloadfilename=opensbi-${PV}.tar.gz;name=opensbi"
SRC_URI[opensbi.sha256sum] = "2cf856a4e5e2e052948ddb54ba48232b1f698b7f52e0374fc7d17d51e8c8f7ce"

S = "${WORKDIR}/opensbi-${PV}"

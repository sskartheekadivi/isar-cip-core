#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2022-2025
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT
#

require recipes-core/ltp/ltp.inc

SRC_URI += "file://0001-listmount04.c-Update-struct-mnt_id_req-support-for-k.patch"

SRC_URI[sha256sum] = "048fa4d69ddbe8a94aa15da9bdc85713ab07a0abbc3de2b8bdd9757644aef1e4"

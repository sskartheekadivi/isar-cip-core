#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2022-2025
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

require secure-boot-secrets.inc

SECRETS_DIR = "${BASE_DISTRO_CODENAME}"
SECRETS_DIR:buster = "bullseye"
SECRETS_DIR:buster:amd64 = "buster"

SB_KEY = "${SECRETS_DIR}/PkKek-1-snakeoil.key"
SB_CERT = "${SECRETS_DIR}/PkKek-1-snakeoil.pem"

DEBIAN_CONFLICTS = "secure-boot-key"

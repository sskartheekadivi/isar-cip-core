#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Jan Kiszka <jan.kiszka@siemens.com>
#
# SPDX-License-Identifier: MIT
#

require recipes-bsp/optee-ftpm/optee-ftpm.inc

SRC_URI += " \
    https://github.com/OP-TEE/optee_ftpm/archive/${SRCREV}.tar.gz;downloadfilename=optee_ftpm-${SRCREV}.tar.gz \
    https://github.com/microsoft/ms-tpm-20-ref/archive/${SRCREV_ms-tpm}.tar.gz;name=ms-tpm;downloadfilename=ms-tpm-20-ref-${SRCREV_ms-tpm}.tar.gz \
    "
#    file://0001-WIP-Update-to-ms-tpm-20-ref-V1.83.patch \
#    file://0001-Do-not-build-ExtMath_Debug_CompatibilityCheck-if-LIB.patch;patchdir=${WORKDIR}/ms-tpm-20-ref-${SRCREV_ms-tpm} \
#    file://0002-Avoid-compiler-warnings-when-Wold-style-definition-i.patch;patchdir=${WORKDIR}/ms-tpm-20-ref-${SRCREV_ms-tpm} \
#    file://0003-Use-correct-header-in-TpmEcc_Util.c.patch;patchdir=${WORKDIR}/ms-tpm-20-ref-${SRCREV_ms-tpm} \
#    file://0004-Fix-BN_WORD_INITIALIZED.patch;patchdir=${WORKDIR}/ms-tpm-20-ref-${SRCREV_ms-tpm} \
#    "
SRCREV = "ce33372ab772e879826361a1ca91126260bd9be1"
#SRCREV_ms-tpm = "ee21db0a941decd3cac67925ea3310873af60ab3"
SRCREV_ms-tpm = "e9fc7b89d865536c46deb63f9c7d0121a3ded49c"

SRC_URI[sha256sum] = "edeb2a1bcf39d80a2e0abb11ba7026af48efd748009ae1f5ab674c1288ff04cb"
#SRC_URI[ms-tpm.sha256sum] = "381e77c4031ba9b2d7337b6ee1ba32534eb02bef8dfaf2530c33db15b58f7370"
SRC_URI[ms-tpm.sha256sum] = "b77d092c0dde362adf6bc88a580ca7c8abe124d69bb734bf28f8904ae30494a4"

S = "${WORKDIR}/optee_ftpm-${SRCREV}"
MS_TPM_20_REF_DIR = "ms-tpm-20-ref-${SRCREV_ms-tpm}"

OPTEE_NAME = "ti-k3"

TA_CPU = "cortex-a53"
TA_DEV_KIT_DIR = "/usr/lib/optee-os/${OPTEE_NAME}/export-ta_arm64"

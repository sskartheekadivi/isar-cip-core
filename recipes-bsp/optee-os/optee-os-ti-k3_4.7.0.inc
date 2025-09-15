#
# Copyright (c) Texas Instruments Incorporated, 2025
#
# SPDX-License-Identifier: MIT

SRC_URI += " \
    https://github.com/OP-TEE/optee_os/archive/${PV}.tar.gz;downloadfilename=optee-os-${PV}.tar.gz \
    "

SRC_URI += "file://stmm.patch"
SRC_URI[sha256sum] = "976b9c184678516038d4e79766608e81d10bf136f76fd0db2dc48f90f994fbd9"

S = "${WORKDIR}/optee_os-${PV}"

DEBIAN_BUILD_DEPENDS += ", device-tree-compiler, python3-cryptography:native"

OPTEE_PLATFORM = "k3-am62x"
OPTEE_NAME = "ti-k3"
OPTEE_EXTRA_BUILDARGS = " \
    CFG_ARM64_core=y \
    CFG_USER_TA_TARGETS=ta_arm64 \
    TEE_IMPL_VERSION=${PV} \
    CFG_TEE_CORE_LOG_LEVEL=4 CFG_TEE_TA_LOG_LEVEL=4 \
    CFG_TA_MEASURED_BOOT=y CFG_CORE_DYN_SHM=y \
    CFG_REE_FS=n CFG_CORE_HEAP_SIZE=524288 CFG_RPMB_FS=y \
    CFG_RPMB_WRITE_KEY=y CFG_RPMB_TESTKEY=y \
    CONFIG_INSECURE=y \
    "

OPTEE_ENABLE_APPS ?= "1"

OVERRIDES .= "${@':optee-with-apps' if bb.utils.to_boolean(d.getVar("OPTEE_ENABLE_APPS")) else ''}"

# StMM integration, required by UEFI auth variable management
DEPENDS:append:optee-with-apps = " edk2-standalonemm-rpmb"
DEBIAN_BUILD_DEPENDS:append:optee-with-apps = ", edk2-standalonemm-rpmb"
OPTEE_EXTRA_BUILDARGS:append:optee-with-apps = " \
    CFG_STMM_PATH=/usr/lib/edk2/BL32_AP_MM.fd \
    "

# OP-TEE fTPM integration
DEPENDS:append:optee-with-apps = " optee-ftpm-${OPTEE_NAME}"
DEBIAN_BUILD_DEPENDS:append:optee-with-apps = ", optee-ftpm-${OPTEE_NAME}"
FTPM_UUID = "bc50d971-d4c9-42c4-82cb-343fb7f37896"
OPTEE_EXTRA_BUILDARGS:append:optee-with-apps = " \
    CFG_EARLY_TA=y \
    EARLY_TA_PATHS=/usr/lib/optee-os/${OPTEE_NAME}/ta/${FTPM_UUID}.stripped.elf \
    "

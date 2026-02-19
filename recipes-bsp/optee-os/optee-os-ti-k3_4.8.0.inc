#
# Copyright (c) Texas Instruments Incorporated, 2025
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

MAINTAINER = "cip-dev <cip-dev@lists.cip-project.org>"

SRC_URI += " \
    https://github.com/OP-TEE/optee_os/archive/${PV}.tar.gz;downloadfilename=optee-os-${PV}.tar.gz \
    "

SRC_URI[sha256sum] = "5222cd553f5edb69ae4ec7cb99b2bfec2c47a47c0be1865b49744701918e8b4d"

S = "${WORKDIR}/optee_os-${PV}"

OPTEE_NAME = "ti-k3"
OPTEE_PLATFORM:ti-am62px-sk = "k3-am62px"
OPTEE_PLATFORM:ti-am62x-sk = "k3-am62x"
OPTEE_EXTRA_BUILDARGS += " \
	CFG_ARM64_core=y \
	CFG_USER_TA_TARGETS=ta_arm64 \
	TEE_IMPL_VERSION=${PV} \
	CFG_TEE_CORE_LOG_LEVEL=1 CFG_TEE_TA_LOG_LEVEL=1 \
	CFG_CORE_DYN_SHM=y CFG_CORE_HEAP_SIZE=524288 \
	CFG_REE_FS=n CFG_RPMB_FS=y \
	CONFIG_INSECURE=y \
	"

DEBIAN_BUILD_DEPENDS += ", device-tree-compiler, python3-cryptography:native"

OPTEE_ENABLE_APPS ?= "1"
OVERRIDES .= "${@':optee-with-apps' if bb.utils.to_boolean(d.getVar("OPTEE_ENABLE_APPS")) else ''}"

#
# Copyright (c) Texas Instruments Incorporated, 2025
#
# SPDX-License-Identifier: MIT

require recipes-bsp/optee-os/optee-os-custom.inc

SRC_URI += " \
    https://github.com/OP-TEE/optee_os/archive/${PV}.tar.gz;downloadfilename=optee_os-${PV}.tar.gz \
    file://rules-ti.tmpl \
"
SRC_URI[sha256sum] = "976b9c184678516038d4e79766608e81d10bf136f76fd0db2dc48f90f994fbd9"

S = "${WORKDIR}/optee_os-${PV}"

DEBIAN_BUILD_DEPENDS += ", python3-cryptography:native"

OPTEE_PLATFORM = "k3-am62x"
OPTEE_NAME = "ti-k3"
OPTEE_EXTRA_BUILDARGS = " \
    TEE_IMPL_VERSION=${PV} \
    CFG_ARM64_core=y \
    CFG_USER_TA_TARGETS=ta_arm64 \
    CFG_TEE_CORE_LOG_LEVEL=1"

OPTEE_BINARIES = "tee-pager_v2.bin"

python do_transform_template:prepend() {
    import shutil
    import os

    src = os.path.join(d.getVar('WORKDIR'), 'rules-ti.tmpl')
    dst = os.path.join(d.getVar('WORKDIR'), 'debian/rules.tmpl')
    shutil.copyfile(src, dst)
}

COMPATIBLE_MACHINE = "ti-am62px-sk"

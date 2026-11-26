#
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require u-boot-common-${PV}.inc

TI_FIRMWARE_PV = "11.02.21"

SRC_URI += " \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-firmware-am62x-gp.bin;downloadfilename=ti-fs-firmware-am62x-gp.bin;name=am62-sysfw-gp \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-firmware-am62x-hs-fs-cert.bin;downloadfilename=ti-fs-firmware-am62x-hs-fs-cert.bin;name=am62-sysfw-cert-hs-fs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-firmware-am62x-hs-fs-enc.bin;downloadfilename=ti-fs-firmware-am62x-hs-fs-enc.bin;name=am62-sysfw-enc-hs-fs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-stub-firmware-am62x-hs-cert.bin;downloadfilename=ti-fs-stub-firmware-am62x-hs-cert.bin;name=am62-sysfw-stub-cert \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-stub-firmware-am62x-hs-enc.bin;downloadfilename=ti-fs-stub-firmware-am62x-hs-enc.bin;name=am62-sysfw-stub-enc \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-firmware-am62x-hs-cert.bin;downloadfilename=ti-fs-firmware-am62x-hs-cert.bin;name=am62-sysfw-cert-hs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-fs-firmware-am62x-hs-enc.bin;downloadfilename=ti-fs-firmware-am62x-hs-enc.bin;name=am62-sysfw-enc-hs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-dm/am62xx/ipc_echo_testb_mcu1_0_release_strip.xer5f;downloadfilename=ipc_echo_testb_mcu1_0_release_strip.xer5f;name=am62-dm \
	file://rules-${MACHINE} \
	file://ti-extra.cfg \
	"

SRC_URI[am62-dm.sha256sum] = "8b4fe60a35c49b5ffe950398d0bd7b48f1db1428524d4e037fb9fe0eb3f84c86"
SRC_URI[am62-sysfw-gp.sha256sum] = "368457718e7dc2c429db7177b31949b5d120ec97563746867fbc4883c94ee1c3"
SRC_URI[am62-sysfw-cert-hs-fs.sha256sum] = "8cb3dd36c84a07dbe75ad2727c9d08fb78e1907102a686f95bcbc59bb248fd47"
SRC_URI[am62-sysfw-enc-hs-fs.sha256sum] = "08f173429099bac16d438dcc456b4c41eebf0dff4610375149e93f3fc06e4612"
SRC_URI[am62-sysfw-stub-cert.sha256sum] = "649d4b2c1082020fa8462d576f18aff29e98eb13f3c9c66b864a383f1b648d7f"
SRC_URI[am62-sysfw-stub-enc.sha256sum] = "d9b0e87d544de50c925301c8490b74117649fb537c5719ccefa0246a607a3aad"
SRC_URI[am62-sysfw-cert-hs.sha256sum] = "8bb1318c77a2927e5a73db83742d40355e7601ebc5b6d7ba9d510547d14d0c0b"
SRC_URI[am62-sysfw-enc-hs.sha256sum] = "eca9660d5f70e2d38479ee87e1adc390295f18c97ed8f144640de742f7b194b2"

S = "${WORKDIR}/u-boot-${PV}"
TI_LINUX_FIRMWARE = "${S}/ti-linux-firmware"

COMPATIBLE_MACHINE = "^(ti-am62x-sk)$"

U_BOOT_R5_CONFIG = "am62x_evm_r5_defconfig"
U_BOOT_A53_CONFIG = "am62x_evm_a53_defconfig"
U_BOOT_CONFIG = "${U_BOOT_A53_CONFIG}"

U_BOOT_BIN_INSTALL = "tiboot3-am62x-hs-fs-evm.bin tispl.bin u-boot.img"

OVERRIDES .= ":ftpm-stmm"

DEPENDS += "trusted-firmware-a-ti-k3 optee-os-ti-k3"
DEBIAN_BUILD_DEPENDS =. "gcc-arm-linux-gnueabihf, \
    swig, python3-dev:native, python3-setuptools, python3-pyelftools, \
    python3-jsonschema:native, yamllint:native, \
    trusted-firmware-a-ti-k3, optee-os-ti-k3,"

do_prepare_build:append() {
    mkdir -p ${TI_LINUX_FIRMWARE}/ti-sysfw
    cp ${WORKDIR}/ti-fs-*firmware-am62x-*.bin ${TI_LINUX_FIRMWARE}/ti-sysfw
    mkdir -p ${TI_LINUX_FIRMWARE}/ti-dm/am62xx
    cp ${WORKDIR}/ipc_echo_testb_mcu1_0_release_strip.xer5f ${TI_LINUX_FIRMWARE}/ti-dm/am62xx

    cp ${WORKDIR}/rules-${MACHINE} ${S}/debian/rules

    sed -ni '/### TI extra config/q;p' ${S}/configs/${U_BOOT_CONFIG}
    cat ${WORKDIR}/ti-extra.cfg >> ${S}/configs/${U_BOOT_CONFIG}
}

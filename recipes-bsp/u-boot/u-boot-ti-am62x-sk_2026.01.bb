#
# Copyright (c) Texas Instruments Incorporated, 2025
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require u-boot-common-${PV}.inc

TI_FIRMWARE_PV = "11.01.16"

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

SRC_URI[am62-dm.sha256sum] = "3b57c0a835253d4a50799bfc5c50cc802e03e5b22f9018e9bd3fc15ea9ce9179"
SRC_URI[am62-sysfw-gp.sha256sum] = "547c289e7ab057ea252b8907e746ae855bc5ea8534017050cccae3ae25e47bb6"
SRC_URI[am62-sysfw-cert-hs-fs.sha256sum] = "8e5e99f948a619b55eded3cae468e16a9b5c30b9dc0b641626285f2e2dabd77f"
SRC_URI[am62-sysfw-enc-hs-fs.sha256sum] = "54017d63d1dddab5a4c3a5aca2d7f4b16158d919fe2d9707f4cf6652a46e0216"
SRC_URI[am62-sysfw-stub-cert.sha256sum] = "b909627fc6478b085adef68590df96aacb0cbe442bf76b24f5b18dc1e3ce851e"
SRC_URI[am62-sysfw-stub-enc.sha256sum] = "e42a59a157ff7735436b9916f18d975c1427d8917386a9f6a34b209a26d2842c"
SRC_URI[am62-sysfw-cert-hs.sha256sum] = "7613fcf3ae98c21343a3d75ae9e01f4bde5ad129972d4434569c8850e4c41e38"
SRC_URI[am62-sysfw-enc-hs.sha256sum] = "5bdadcb8b927d9d72752f25a214100cc2e6421c844aded6cbf90a511757b903c"

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

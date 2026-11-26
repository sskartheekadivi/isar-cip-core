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
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x-gp.bin;downloadfilename=ti-sci-firmware-am64x-gp.bin;name=am64-sysfw-gp \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x-hs-cert.bin;downloadfilename=ti-sci-firmware-am64x-hs-cert.bin;name=am64-sysfw-cert-hs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x-hs-enc.bin;downloadfilename=ti-sci-firmware-am64x-hs-enc.bin;name=am64-sysfw-enc-hs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x_sr2-hs-cert.bin;downloadfilename=ti-sci-firmware-am64x_sr2-hs-cert.bin;name=am64-sr2-sysfw-cert-hs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x_sr2-hs-enc.bin;downloadfilename=ti-sci-firmware-am64x_sr2-hs-enc.bin;name=am64-sr2-sysfw-enc-hs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x_sr2-hs-fs-cert.bin;downloadfilename=ti-sci-firmware-am64x_sr2-hs-fs-cert.bin;name=am64-sr2-sysfw-cert-hs-fs \
	https://github.com/TexasInstruments/ti-linux-firmware/raw/refs/tags/${TI_FIRMWARE_PV}/ti-sysfw/ti-sci-firmware-am64x_sr2-hs-fs-enc.bin;downloadfilename=ti-sci-firmware-am64x_sr2-hs-fs-enc.bin;name=am64-sr2-sysfw-enc-hs-fs \
	file://rules-${MACHINE} \
	file://ti-extra.cfg \
	"

SRC_URI[am64-sysfw-gp.sha256sum] = "2af5152c39cf1994c05bbb66aa0f44162dd7f2085a8a12245ad339ef2a6e5984"
SRC_URI[am64-sysfw-cert-hs.sha256sum] = "f64fd89d9f1c450f40054bac6514d76cd24c3289895db146ebe07f538119f15c"
SRC_URI[am64-sysfw-enc-hs.sha256sum] = "8e3960728033a042ecbf8b174e247e3c44b268f8d0593978e50e6bf2f20f332d"
SRC_URI[am64-sr2-sysfw-cert-hs.sha256sum] = "4579904840c7955761378f9f30f5a730026cba20f0e0f0b14735b6327d9b2ddc"
SRC_URI[am64-sr2-sysfw-enc-hs.sha256sum] = "c7968dfd2defec2c147091447eb418fe0d122f954884a386a4a5491d8062a80d"
SRC_URI[am64-sr2-sysfw-cert-hs-fs.sha256sum] = "96790bba8a790bde9188b7e8acddc87deae0c5d5277d5d1922825218ec476dfa"
SRC_URI[am64-sr2-sysfw-enc-hs-fs.sha256sum] = "7fc57d4c4c7b310f7c3eb627c6472a18cdfd18e75c705ca6dc96c5a4326919a2"

S = "${WORKDIR}/u-boot-${PV}"
TI_LINUX_FIRMWARE = "${S}/ti-linux-firmware"

COMPATIBLE_MACHINE = "^(ti-am64x-sk)$"

U_BOOT_R5_CONFIG = "am64x_evm_r5_defconfig"
U_BOOT_A53_CONFIG = "am64x_evm_a53_defconfig"
U_BOOT_CONFIG = "${U_BOOT_A53_CONFIG}"

U_BOOT_BIN_INSTALL = "tiboot3-am64x_sr2-hs-fs-evm.bin tispl.bin u-boot.img"

OVERRIDES .= ":ftpm-stmm"

DEPENDS += "trusted-firmware-a-ti-k3 optee-os-ti-k3"
DEBIAN_BUILD_DEPENDS =. "gcc-arm-linux-gnueabihf, \
    swig, python3-dev:native, python3-setuptools, python3-pyelftools, \
    python3-jsonschema:native, yamllint:native, \
    trusted-firmware-a-ti-k3, optee-os-ti-k3,"

do_prepare_build:append() {
    mkdir -p ${TI_LINUX_FIRMWARE}/ti-sysfw
    cp ${WORKDIR}/ti-sci-firmware-am64x*.bin ${TI_LINUX_FIRMWARE}/ti-sysfw

    cp ${WORKDIR}/rules-${MACHINE} ${S}/debian/rules

    sed -ni '/### TI extra config/q;p' ${S}/configs/${U_BOOT_CONFIG}
    cat ${WORKDIR}/ti-extra.cfg >> ${S}/configs/${U_BOOT_CONFIG}
}

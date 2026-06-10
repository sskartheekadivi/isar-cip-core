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

SRC_URI[am64-sysfw-gp.sha256sum] = "df1813a37ed8058aff2cf20ff0f1cfb90df8a3d5a968a4d92f96f47587fc2262"
SRC_URI[am64-sysfw-cert-hs.sha256sum] = "39bd55b893b90df959b54d4f474e5f061245ed7a6b31c18548b0990bcbd6be29"
SRC_URI[am64-sysfw-enc-hs.sha256sum] = "87cb98cf3cf3b231daba3d753de8f225e895d3f82db76d21a26940b5b7e22ff3"
SRC_URI[am64-sr2-sysfw-cert-hs.sha256sum] = "6781211c36f6ad36950da4e45297b409cf3355d88e8cb66cff08cc20ad45fe34"
SRC_URI[am64-sr2-sysfw-enc-hs.sha256sum] = "c0d68db3ea4a7d7cd88fd6938aa3a67c483341e38cc29771a8da0a56dbb0e9f8"
SRC_URI[am64-sr2-sysfw-cert-hs-fs.sha256sum] = "38fa902f846351770e2026e8af8b9a0320fc0259e801ae162e04d8a8700547ce"
SRC_URI[am64-sr2-sysfw-enc-hs-fs.sha256sum] = "694669f6fc0d714657ff33af0b3b1c86665808f5ca93a769cca40de2a71517a6"

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

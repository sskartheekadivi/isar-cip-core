#
# Copyright (c) 2025 Texas Instruments Incorporated - http://www.ti.com
#
# SPDX-License-Identifier: MIT

require u-boot-common-${PV}.inc

TI_FIRMWARE_SRCREV = "33bec0e2ea9a25362c0c8243b2f1ed392dbc5b02"

SRC_URI += " \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-sysfw/ti-fs-firmware-am62px-hs-fs-cert.bin?id=${TI_FIRMWARE_SRCREV};downloadfilename=ti-fs-firmware-am62px-hs-fs-cert.bin;name=sysfw-cert-hs-fs \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-sysfw/ti-fs-firmware-am62px-hs-fs-enc.bin?id=${TI_FIRMWARE_SRCREV};downloadfilename=ti-fs-firmware-am62px-hs-fs-enc.bin;name=sysfw-enc-hs-fs \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-sysfw/ti-fs-stub-firmware-am62px-hs-cert.bin?id=${TI_FIRMWARE_SRCREV};downloadfilename=ti-fs-stub-firmware-am62px-hs-cert.bin;name=sysfw-stub-cert \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-sysfw/ti-fs-stub-firmware-am62px-hs-enc.bin?id=${TI_FIRMWARE_SRCREV};downloadfilename=ti-fs-stub-firmware-am62px-hs-enc.bin;name=sysfw-stub-enc \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-sysfw/ti-fs-firmware-am62px-hs-cert.bin?id=${TI_FIRMWARE_SRCREV};downloadfilename=ti-fs-firmware-am62px-hs-cert.bin;name=sysfw-cert-hs \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-sysfw/ti-fs-firmware-am62px-hs-enc.bin?id=${TI_FIRMWARE_SRCREV};downloadfilename=ti-fs-firmware-am62px-hs-enc.bin;name=sysfw-enc-hs \
    https://git.ti.com/cgit/processor-firmware/ti-linux-firmware/plain/ti-dm/am62pxx/ipc_echo_testb_mcu1_0_release_strip.xer5f?id=${TI_FIRMWARE_SRCREV};downloadfilename=ipc_echo_testb_mcu1_0_release_strip.xer5f;name=dm \
    file://0001-configs-KASLR-OPTEE-RNG-support-for-K3-devic.patch \
    file://rules-ti.tmpl"

SRC_URI[dm.sha256sum] = "7251394068d287ec81cb0f005a734de3bb7bb695d9dbcc3903ea83dda2182efc"
SRC_URI[sysfw-cert-hs-fs.sha256sum] = "fe36da7923ac6fa52eea5e601fb4321299df5e639d9dd66b41b6825bc1d9d539"
SRC_URI[sysfw-enc-hs-fs.sha256sum] = "4db6ed096fd5077023f292841a64aedb3a4ff8408d1160fb10a7aa0355d06e56"
SRC_URI[sysfw-stub-cert.sha256sum] = "22b58a07429da5ae0b0cd126d4ec0f429ee687429251f4fcfe9a2f5bb3e74b5d"
SRC_URI[sysfw-stub-enc.sha256sum] = "2cb73e5c9acf985325f40f16152906c9a2057f41ffb7903a981a34f7b2aaf524"
SRC_URI[sysfw-cert-hs.sha256sum] = "dfb1c90110155328bc19d9ecdbfacc5a899d4cce8378f8eb8f27877c9fda1fdc"
SRC_URI[sysfw-enc-hs.sha256sum] = "4d5216f8187441ef8af00ae7e79113d36964221a3a19a026aeec09e20a290931"

S = "${WORKDIR}/u-boot-${PV}"
TI_LINUX_FIRMWARE = "${S}/ti-linux-firmware"
BINMAN_INDIRS = "./ti-linux-firmware"

COMPATIBLE_MACHINE = "ti-am62px-sk"

U_BOOT_R5_CONFIG = "am62px_evm_r5_defconfig"
U_BOOT_A53_CONFIG = "am62px_evm_a53_defconfig"
U_BOOT_CONFIG = "${U_BOOT_A53_CONFIG}"

U_BOOT_BIN_INSTALL = "tiboot3-am62px-hs-fs-evm.bin tispl.bin u-boot.img"

DEPENDS += "trusted-firmware-a-ti-k3 optee-os-ti-k3"
DEBIAN_BUILD_DEPENDS =. "gcc-arm-linux-gnueabihf, \
    libssl-dev:native, libssl-dev, grub-common, \
    swig, python3-dev:native, python3-setuptools, python3-pyelftools, \
    python3-jsonschema:native, python3-yaml:native, \
    trusted-firmware-a-ti-k3, optee-os-ti-k3,"

do_prepare_build:append() {
    mkdir -p ${TI_LINUX_FIRMWARE}/ti-sysfw
    cp ${WORKDIR}/ti-fs-*firmware-am62px-hs*.bin ${TI_LINUX_FIRMWARE}/ti-sysfw
    mkdir -p ${TI_LINUX_FIRMWARE}/ti-dm/am62pxx
    cp ${WORKDIR}/ipc_echo_testb_mcu1_0_release_strip.xer5f ${TI_LINUX_FIRMWARE}/ti-dm/am62pxx
}

TEMPLATE_VARS += "BINMAN_INDIRS U_BOOT_R5_CONFIG U_BOOT_A53_CONFIG"

python do_transform_template:prepend() {
    import shutil
    import os

    src = os.path.join(d.getVar('WORKDIR'), 'rules-ti.tmpl')
    dst = os.path.join(d.getVar('WORKDIR'), 'debian/rules.tmpl')
    shutil.copyfile(src, dst)
}

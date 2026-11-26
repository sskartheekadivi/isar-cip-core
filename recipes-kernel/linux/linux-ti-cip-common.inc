#
# CIP Core, generic profile
#
# Copyright (c) Texas Instruments Incorporated, 2026
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

require linux-cip-common.inc

# The TI CIP kernel carries its own defconfig. Use it instead of the one from
# cip-kernel-config, no matter what the machine asks for.
USE_CIP_KERNEL_CONFIG = "0"
KERNEL_DEFCONFIG = "defconfig"

# Prune the config down to what the TI platforms need, as done for TI's own
# builds. This fragment is part of the kernel sources, so it gets applied
# before the ones below.
KERNEL_CONFIG_FRAGMENTS:append = " ${S}/kernel/configs/ti_arm64_prune.config"

# The TI defconfig misses options that CIP Core features depend on, and has
# CONFIG_TCG_TPM enabled, which pulls in CONFIG_HW_RANDOM_TPM.
SRC_URI += " \
    file://ti-cip-core.cfg \
    file://no-tpm-hwrng.cfg \
    "

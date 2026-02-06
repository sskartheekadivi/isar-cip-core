# Enable Factory-reset

To enable the factory-reset functionality the following two elements need to be installed:
- initramfs-factory-reset-hook which executes the factory-reset
```
INITRAMFS_INSTALL += "initramfs-factory-reset-hook"
```
- factory-reset-helper which provides the helper scripts to set the factory-reset marker
```
IMAGE_INSTALL += "factory-reset-helper"
```

## Technical details

By executing `set-factory-reset.sh --create`, a marker is stored as an EFI variable or on disk (default `/dev/disk/by-partlabel/var`).
The marker on disk is intended for devices which do not provide the facilities to set EFI variables.
The initramfs reads this marker and formats the hard-drives provided by the variable `INITRAMFS_FACTORY_RESET_DEVICES`.
The initramfs-hook will try to detect the previous used file system and will format the partition with the detected type.

### Disk encryption
[clevis](https://github.com/latchset/clevis/blob/2ad7027b7182b8f46404f8ec0735a83738f7ca7f/src/pins/tpm2/clevis-encrypt-tpm2#L192C8-L192C26)
and [systemd](https://github.com/systemd/systemd/blob/114ad16c4dcac136fb7646866f8a95ee5631ee51/src/shared/tpm2-util.c#L5281)
use the primary key of TPM. To ensure that the key is not reused the factory-reset deletes the primary key from the TPM
before formatting the selected hard drives with a new LUKS container.

The removal of the TPM keys can be deactivated by setting the variable `INITRAMFS_FACTORY_RESET_CLEAR_TPM` to `0`.
As the factory-reset is executed before the disks are unlocked in the initramfs, the file system type must be provided with the
variable `INITRAMFS_FACTORY_RESET_LUKS_FORMAT_TYPE`. The factory-reset of encrypted partition is not supported with the file based
marker.

## Verification

- Create a test file in a target partition (ex: `/var`)
```
root@demo:~# touch /var/to-be-deleted
```

- Set the factory-reset marker with the below command:
```
root@demo:~# set-factory-reset.sh --create
```

- Trigger a reboot as factory-reset happens in the initramfs step:
```
root@demo:~# reboot
```

- Factory reset runs during the initramfs. The reset scripts format the target partition with the required filesystem. If disk encryption is enabled, the initramfs scripts also encrypts the target partitions. Both of which can be observed in the boot logs.

- After the reboot, confirm that the test file created in step 1 is deleted.
```
root@demo:~# find /var -name "to-be-deleted"
```

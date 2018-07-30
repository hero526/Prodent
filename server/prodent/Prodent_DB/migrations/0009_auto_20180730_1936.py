# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2018-07-30 19:36
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Prodent_DB', '0008_auto_20180730_1931'),
    ]

    operations = [
        migrations.RenameField(
            model_name='db_access',
            old_name='Id',
            new_name='Uid',
        ),
        migrations.RemoveField(
            model_name='db_access',
            name='Carrier',
        ),
        migrations.AddField(
            model_name='db_access',
            name='Provider',
            field=models.CharField(default=None, max_length=20, verbose_name='Provider'),
        ),
    ]

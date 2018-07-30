from django.db import models

# Create your models here.
class DB_access(models.Model):
    Uid = models.CharField('Uid', unique=True, default=None, max_length=20)
    Name = models.CharField('Name', default=None, max_length=20)
    Cash = models.IntegerField(default=5)
    MAC = models.CharField('MAC', default=None, max_length=20)
    Rate = models.FloatField(default=0.0)
    Coord_x = models.FloatField(default=0.0)
    Coord_y = models.FloatField(default=0.0)
    Password = models.CharField('Password', default=None, max_length=20)
    PhoneNumber = models.CharField('PhoneNumber', default=None, max_length=20)
    Provider = models.CharField('Provider', default=None, max_length=20)


    def dic(self):
        fields=['Uid', 'Name', 'Cash', 'MAC', 'Rate', 'Coord_x', 'Coord_y', 'Password', 'PhoneNumber', 'Provider']
        result = {}
        for field in fields:
            result[field]=self.__dict__[field]
        return result


    def __str__(self):
        return self.Name

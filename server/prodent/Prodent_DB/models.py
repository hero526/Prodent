from django.db import models

# Create your models here.
class DB_access(models.Model):
    Name = models.CharField('Name', unique=True, default=None, max_length=20)
    Cash = models.IntegerField(default=5)
    MAC = models.CharField('MAC', default=None, max_length=20)
    Rate = models.FloatField(default=0.0)
    Coord_x = models.FloatField(default=0.0)
    Coord_y = models.FloatField(default=0.0)


    def dic(self):
        fields=['Name', 'Cash', 'MAC', 'Rate', 'Coord_x', 'Coord_y']
        result = {}
        for field in fields:
            result[field]=self.__dict__[field]
        return result


    def __str__(self):
        return self.Name

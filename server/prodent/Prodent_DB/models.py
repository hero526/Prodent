from django.db import models

# Create your models here.
class Prodent_DB(models.Model):
    Name = models.CharField('Name', default=None, max_length=20)
    Cash = models.IntegerField(default=5)
    Mac = models.CharField('Mac', default=None, max_length=20)
    Rate = models.FloatField(default=0.0)
    Coord_x = models.FloatField(default=0.0)
    Coord_y = models.FloatField(default=0.0)
    
    def __str__(self):
        return self.Title

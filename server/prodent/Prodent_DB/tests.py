from .models import Prodent_DB

from django.test import TestCase

# Create your tests here.
class ShowallTestCase(TestCase):
    def test_generate(self):
        p = Prodent_DB()
        print(p.Name)

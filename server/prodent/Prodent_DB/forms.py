from django import forms
from .models import DB_access

class PostForm(forms.ModelForm):

    class Meta:
        model = DB_access
        fields = ('Name', 'Cash', 'Rate', 'Coord_x', 'Coord_y', 'Uid', 'Password', 'PhoneNumber', 'Provider')


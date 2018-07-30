class DBSerializer(serializers.ModelSerializer):
    class Meta:
        model = Prodent_DB
        fields = ('Uid', 'Name', 'Cash', 'Mac', 'Rate', 'Coord_x', 'Coord_y', 'Password', 'PhoneNumber', 'Provider')

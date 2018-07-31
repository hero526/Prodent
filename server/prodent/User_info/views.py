from django.shortcuts import render, redirect
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt

from .models import DB_access
from .forms import PostForm
import json

# Create your views here.

def testView(request):
    return HttpResponse('<h2>dbView!</h2>')

@csrf_exempt
def getData(request, Uid=None):
    try:
        entries = DB_access.objects.get(Uid=Uid)
        data = entries.dic()
        return HttpResponse(json.dumps(data), content_type="application/json")
    except : 
        return HttpResponse("No Correct Data")


@csrf_exempt
def new_post(request):
    if request.method == "POST":
        # form = PostForm(request.POST);
        print(request.body)
        info = json.loads(request.body)
        target = DB_access.objects.get(Uid = info['Uid'])
        if target != None:
            target.delete()

        if info['Name'] != 'test':
            user = DB_access(Name=info['Name'], Cash=info['Cash'], MAC=info['MAC'], Rate=info['Rate'], Coord_x=info['Coord_x'],  Coord_y=info['Coord_y'], Uid=info['Uid'], Password=info['Password'], PhoneNumber=info['PhoneNumber'], Provider=info['Provider'])
            user.save()
            return HttpResponse("OK")
        else:
            return HttpResponse("Fail")
        '''
        if form.is_valid():
        #    row = form.save(commit = False)
        #    row.generate()
            
            return HttpResponse("OK")
        else:
            return HttpResponse("Fail")
        ''' 
    else:
       return HttpResponse("Wrong Command Occur!")


@csrf_exempt
def Databylocale(request, Uid=None):
    try:
        min_distance = None
        entries = DB_access.objects.get(Uid = Uid)
        target_data = entries.dic()
        target_x = target_data['Coord_x']
        target_y = target_data['Coord_y']
        rows = DB_access.objects.filter(Status=2)
        ret_str = ""
        for row in rows:
            ret_str += str(json.dumps(row.dic()))
            ret_str += ','
            
        return HttpResponse(ret_str[:-1], content_type="application/json")
    
    except:
        return HttpResponse("Invalid data")

from django.shortcuts import render, redirect
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt

from .models import DB_access
from .forms import PostForm
import json

# Create your views here.

def testView(request):
    return HttpResponse('<h2>dbView!</h2>')


def getData(request, tag=None):
    try:
        entries = DB_access.objects.get(Uid = tag)
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
        if info['Name'] != None:
            
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

def loadData(request):
    
    return request




public int addIntentOptions(int group, int id, int categoryOrder,
                     ComponentName caller,
                     Intent[] specifics,
                     Intent intent, int flags,
                     MenuItem[] outSpecificItems)
{
   PackageManager pm = mContext.getPackageManager();
   final List<ResolveInfo> lri =
      pm.queryIntentActivityOptions(caller, specifics, intent, 0);
      
   final int N = lri != null ? lri.size() : 0;
   
   if ((flags & FLAG_APPEND_TO_GROUP) == 0) 
   {
      removeGroup(group);
   }
   
   for (int i=0; i<N; i++) 
   {
      final ResolveInfo ri = lri.get(i);
      Intent rintent = new Intent(
              ri.specificIndex < 0 ? intent : specifics[ri.specificIndex]);
            
      rintent.setComponent(new ComponentName(
                  ri.activityInfo.applicationInfo.packageName,
                  ri.activityInfo.name));
                  
      final MenuItem item = add(group, id, categoryOrder, ri.loadLabel(pm));
      
      item.setIntent(rintent);
      if (outSpecificItems != null && ri.specificIndex >= 0) 
      {
         outSpecificItems[ri.specificIndex] = item;
      }
   }
   return N;
}



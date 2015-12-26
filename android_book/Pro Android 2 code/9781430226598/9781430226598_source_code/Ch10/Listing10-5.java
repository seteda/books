//Figure out how you want to arrange your points
short[] myIndecesArray = {0,1,2};

//get a short buffer
java.nio.ShortBuffer mIndexBuffer;

//Allocate 2 bytes each for each index value
ByteBuffer ibb = ByteBuffer.allocateDirect(3 * 2);
ibb.order(ByteOrder.nativeOrder());
mIndexBuffer = ibb.asShortBuffer();

//stuff that into the buffer
for (int i=0;i<3;i++)
{
   mIndexBuffer.put(myIndecesArray[i]);
}

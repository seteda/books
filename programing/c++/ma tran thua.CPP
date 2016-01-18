//--De tai thuc tap co so----------|
// 1. Cho vi du ve ma tran thua----|
// 2. Xay dung cac cau truc du lieu|
// 3. Cai dat cac phep toan---------
#include <stdio.h>
#include <conio.h>
//#include <stdlib.h>
#include <math.h>
//------------Dinh nghia cau truc cua 1 nut-------------------------------------
typedef struct tagNode//dinh nghia kieu cau truc moi, khi do, tagNode la ten kieu.
{
	int data,row,col;//du lieu cua nut, dia chi hang, dia chi cot
	struct tagNode *hnext;//con tri lien ket danh sach hang
	struct tagNode *vnext;//con tri lien ket danh sach cot
}Node;//ten bien cau truc
//-------Dinh nghia ma tran thua------------------------------------------------
struct SpMat
{
	int n;//kich thuoc ma tran
	Node* rows;//con tro hang
	Node* cols;//con tro cot
};
//---------------tao 1 nut moi de them vao danh sach----------------------------
Node *GetNode(int iRow, int iCol, int iKey)//tham so truyen vao: chi so hang, chi so cot, gia tri cua nut, gia tri tra ve la con tro node
{
	Node *pNew = new Node();//cap phat dong cho con tro pNew 1 vung nho=kich thuoc cua 1 nut
	pNew->row = iRow;
	pNew->col = iCol;
	pNew->hnext = NULL;//chua duoc lien ket?
	pNew->vnext = NULL;//chua duoc lien ket?
	pNew->data = iKey;
	return pNew;//tra con tro ve cho ham
}
//-----------------them mot nut vao ben phai cua danh sach---------------------- CHUA HIEU
void addRight(Node* &pLeft ,Node* &pRight)//tham so: kieu con tro Node, truyen vao la tham chieu=> tham so duoc truyen phai co kieu con tro
{
	int iRow,iCol;
	Node *pNew = GetNode(iRow, iCol, 0);//con tro pNew duoc gan bang con tro Getnode, ca 2 deu cung kieu Nude nen gan duoc
	if(pLeft == NULL)
	{
		pLeft = pRight = pNew;
	}
	else
	{
		pRight->hnext = pNew;
		pRight = pNew;
	}
}
//-----Khoi tao danh sach ma tran thua------------------------------------------
void spInit(SpMat* matrix, int n)//kieu SpMat co 3 thong so: kich thuoc n, con tro hang va cot
{
	Node* pLeftTop = NULL;
	Node* pRightBottom = NULL;
	for(int i = 0; i < n*n; i++)  //khoi tao gi tri dau cho n*n phan tu cua danh sach lien ket
		addRight(pLeftTop, pRightBottom); //ham tu tao
	Node**ppLeftRow;
	ppLeftRow = new Node*[n];
	Node *pTmp = pLeftTop;
	for(int i = 0 ; i < n*n ; i++)
	{
		if(i%n == 0)
			ppLeftRow[i/n] = pTmp;
		pTmp=pTmp->hnext;
	}
	for(int i = 0 ; i < n-1; i++)
	{
		Node *p = ppLeftRow[i];
		Node *q = ppLeftRow[i+1];
		for(int j = 0 ; j < n; j++)
		{
			if( j == n-1)
			{
			     p->hnext = NULL;
			     p->vnext = q;
			}
			else
			{
				p->vnext = q;
				p = p->hnext;
				q = p->hnext;
			}
		}
	}
	matrix->n=n;
	matrix->cols = pLeftTop;
	matrix->rows= pLeftTop;
}
//---------------them 1 nut vao trong ma tran thua------------------------------
void spAddNode(SpMat *mat, int row, int col, int data)
{
	Node *pLeftTop = mat->cols;
	for(int i = 0 ; i < row ;i++)
		pLeftTop = pLeftTop->vnext;
	for(int j = 0 ; j < col;j++)
		pLeftTop = pLeftTop->hnext;
	pLeftTop->data = data;
}
//CAC HAM UNG DUNG CUA MA TRAN THUA
//----------Tinh tong cua 2 ma tran---------------------------------------------
void spAddMat(SpMat *a, SpMat *b, SpMat *c)
{
	int n = a->n;
	Node *pTopLeftA = a->cols;
	Node *pTopLeftB = b->cols;
	Node *pTopLeftC = c->cols;
	//-------------------------
	for(int i = 0 ; i < n; i++)
	{
		Node* pRowA = pTopLeftA;
		Node* pRowB = pTopLeftB;
		Node* pRowC = pTopLeftC;
		for(int j = 0 ; j < n; j++)
		{
			pRowA->data = pRowB->data + pRowC->data;
			pRowA =pRowA->hnext;
			pRowB =pRowB->hnext;
			pRowC =pRowC->hnext;
		}
		pTopLeftA= pTopLeftA->vnext;
		pTopLeftB= pTopLeftB->vnext;
		pTopLeftC= pTopLeftC->vnext;
	}

}
//----------Ham tinh hieu cua 2 ma tran thua------------------------------------
void spSubMat(SpMat *a,SpMat *b,SpMat *c)
{
	int n=a->n;
	Node *pTopLeftA=a->cols;
	Node *pTopLeftB=b->cols;
	Node *pTopLeftC=c->cols;
	//------------------------
	for(int i=0;i<n;i++)
	{
	    Node *pRowA=pTopLeftA;
	    Node *pRowB=pTopLeftB;
	    Node *pRowC=pTopLeftC;
	    for(int j=0;j<n;j++)
	    {
		pRowA->data=pRowB->data-pRowC->data;
		pRowA=pRowA->hnext;
		pRowB=pRowB->hnext;
		pRowC=pRowC->hnext;
	    }
	    pTopLeftA=pTopLeftA->vnext;
	    pTopLeftB=pTopLeftB->vnext;
	    pTopLeftC=pTopLeftC->vnext;
	}
}
//------nhan ma tran voi 1 so nguyen--------------------------------------------
void spmulconst(SpMat *a,int k)
{
	int n=a->n;
	Node *pTopLeftA=a->cols;
	//------------------------
	for(int i=0;i<n;i++)
	{
	    Node *pRowA=pTopLeftA;
	    for(int j=0;j<n;j++)
	    {
		pRowA->data=pRowA->data*k;
		pRowA=pRowA->hnext;
	    }
	    pTopLeftA=pTopLeftA->vnext;
	}
}
      //--------------------------
void spMulMat(SpMat *a, SpMat *b, SpMat *c)
{
	int n = a->n;
	Node *pTopLeftA = a->cols;
	Node *pTopLeftB = b->rows;
	Node *pTopLeftC = c->cols;
	//-----------------------------
	for(int i = 0 ; i < n; i++)
	{
		Node* pRowA = pTopLeftA;
		Node* pRowB = pTopLeftB;
		Node* pColC = pTopLeftC;
		int temp=0;
		for(int j = 0 ; j < n; j++)
		{
			temp= temp+pRowB->data*pColC->data;
			pRowA->data=temp;
			pRowB =pRowB->hnext;
			pColC =pColC->vnext;
		}
			pTopLeftA= pTopLeftA->vnext;
			pTopLeftB= pTopLeftB->vnext;
			//pTopLeftC= pTopLeftC->hnext;
	}
		pTopLeftC= pTopLeftC->hnext;
}

//--------Ham tinh dinh thuc cua ma tran thua-----------------------------------
void spDetMat(SpMat *a)
{
   //chua hoan thanh
}
//------Ham in cac phan tu trong ma tran thua-----------------------------------
void printf(SpMat *mat)
{
	int n = mat->n;
	Node *pTopLeft = mat->cols;
	for(int i = 0 ; i < n; i++)
	{
		Node* pRow = pTopLeft;
		for(int j = 0 ; j < n; j++)
		{
			printf("   %d\t", pRow->data);
			pRow =pRow->hnext;
		}
		printf("\n\n");
		pTopLeft= pTopLeft->vnext;
	}
}
//------------Chuong trinh chinh------------------------------------------------
main()
{
	int k;
	SpMat matrix1;
	spInit(&matrix1, 3);
	for (int i = 0 ; i < 3 ; i++)
	{
		for ( int j = 0 ; j < 3; j++)
		{
			spAddNode(&matrix1, i , j,3*i+j);
		}
	}
	printf("matrix 1: \n");
	printf(&matrix1);
	SpMat matrix2;
	spInit(&matrix2, 3);

	for (int i = 0 ; i < 3 ; i++)
	{
		for (int   j = 0 ; j < 3; j++)
		{
			spAddNode(&matrix2, i , j, 3*i + j + 1);
		}
	}
	printf("matrix 2: \n");
	printf(&matrix2);
	printf("Tong cua 2 ma tran tren la: \n");
	printf("----------------------------\n");
	SpMat matrix3;
	spInit(&matrix3,3);
	spAddMat(&matrix3,&matrix1,&matrix2);
	printf(&matrix3);
	printf("Hieu cua 2 ma tran tren la: \n");
	printf("----------------------------\n");
	SpMat matrix4;
	spInit(&matrix4,3);
	spSubMat(&matrix4,&matrix1,&matrix2);
	printf(&matrix4);
	printf("Tich cua 2 ma tran tren la: \n");
	printf("----------------------------\n");
	SpMat matrix5;
	spInit(&matrix5,3);
	spMulMat(&matrix5,&matrix1,&matrix2);
	printf(&matrix5);
	printf("Nhap k=");scanf("%d",&k);
	printf("Ma tran A sau khi nhan voi K la:\n");
	printf("-------------------------------\n");
	spmulconst(&matrix1,k);
	printf(&matrix1);
	getch();
}

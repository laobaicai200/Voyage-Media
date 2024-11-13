<template>
    <div>

   <!--查询表单-->
<div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="分类名称">
              <el-input @input="fetchPageList()" style="width: 100%" v-model="searchObj.name" placeholder="分类名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini"  @click="fetchPageList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>
        
            <!-- 工具条 -->
<div class="tools-div">
  <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
</div>


<el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" >
      <el-form ref="dataForm" :model="sysCategory" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="分类名称">
          <el-input v-model="sysCategory.name"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
</el-dialog>


       <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;">

      <el-table-column
        label="序号"
        width="180"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="createTime" label="创建时间" />
       <el-table-column prop="updateTime" label="修改时间" width="200"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除"/>

        </template>
      </el-table-column>
    </el-table>

      <!-- 分页组件 -->
  <el-pagination
    :current-page="page"
    :total="total"
    :page-size="limit"
    style="padding: 30px 0; text-align: center;"
    layout="total, prev, pager, next, jumper"
    @current-change="fetchPageList"
  />


    </div>
</template>

<script>

// 导入 
import api from '@/api/category/category.js'

export default {
   data(){
    return{
        list:[],
        page:1,
        limit:5,
        tota:0,
        searchObj:{},
        listLoading:true,
        dialogVisible:false,
        sysCategory:{}
    }
   },
   created () {
    //this.listLoading=true;
     this.fetchPageList();
   },
   methods:{
    edit(id){
      //1.弹框
      this.dialogVisible=true;
      //2.赋值
      api.findCategoryById(id)
      .then(response=>{
        this.sysCategory = response.data;
      })
    },
    saveOrUpdate(){
       if(this.sysCategory.id!=null)
       {
         this.updateCategory();
       }
       else
       {
        this.addCategory();
       }
    },
    addCategory(){
       api
       .addCategory(this.sysCategory)
       .then(response=>{
             this.$message({
                type: 'success',
                message: '添加成功!'
              });
           //关闭弹框
           this.dialogVisible=false;
           // 刷新列表
           this.fetchPageList();   

       })
    },
    updateCategory(){
       api
       .updateCategory(this.sysCategory)
       .then(response=>{
             this.$message({
                type: 'success',
                message: '修改成功!'
              });
           //关闭弹框
           this.dialogVisible=false;
           // 刷新列表
           this.fetchPageList();   

       })
    },
    add(){
      this.dialogVisible=true;
      this.sysCategory={};
    },
    removeDataById(id){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.removeCategoryById(id).then(response=>{
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.fetchPageList();
          })

        })
    },
    resetData(){
     //1.清空搜索条件
     this.searchObj={};
     //2.再次刷新列表
     this.fetchPageList();
    },
     fetchPageList(page=1){
        this.page=page;
        api.getCategoryPageInfo(this.page,this.limit,this.searchObj).then(response=>{
            this.listLoading=false;
            // console.log(response);
            this.list = response.data.records;
            this.total = response.data.total;
        })
     }
   }
}
</script>

<style lang="less" scoped>

</style>
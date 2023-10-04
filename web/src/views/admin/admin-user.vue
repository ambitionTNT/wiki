<template>
  <div class="admin">
    <a-layout>

      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >

        <p>
          <a-form
              layout="inline"
              :model="param"
          >
            <a-form-item>
              <a-input v-model:value="param.loginName" placeholder="登陆名">
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" danger @click="handleQuery({
                page: 1,
                size: pagination.pageSize
              })"
              >
                查询
              </a-button>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" danger @click="add()"  >
                新增
              </a-button>
            </a-form-item>

          </a-form>




        </p>
        <br>
          <a-table
              :columns="columns"
              :row-key="record => record.id"
              :data-source="users"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
          >

            <template v-slot:cover="{ text: cover }">
              <img v-if="cover" :src="cover" alt="avatar" />
            </template>

<!--             eslint-disable-next-line-->
            <template v-slot:action="{ record,text }">
              <a-space size="small">
              
                <router-link :to="'/admin/user?userId=' +record.id">
                  <a-button type="primary"  @click = "resetPassword(record)" >
                    重置密码

                  </a-button>
                </router-link>


                <a-button type="primary" @click="edit(record)" >
                  编辑

                </a-button>

                <a-popconfirm
                    title="删除后不可回复，您确认要删除?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="handleDelete(record.id)"

                >
                  <a-button type="danger" >
                    删除
                  </a-button>
                </a-popconfirm>

              </a-space>
            </template>
          </a-table>
      </a-layout-content>
    </a-layout>

    <a-modal v-model:open="open"
             title="用户表单"
             @ok="handleModelOk"

    >

      <a-form :model="user" :label-col="{span : 6}"
              >
        <a-form-item label="登陆名"  :rules="[{ required: true, message: '名称不能为空!' }]">
          <a-input v-model:value="user.loginName" :disabled = "!!user.id"
                  />
        </a-form-item>
        <a-form-item label="昵称" :rules="[{ required: true, message: '名称不能为空!' }]">
          <a-input v-model:value="user.name"
                   />
        </a-form-item>
        <a-form-item label="密码 "
                     :rules="[{ required: true, message: '名称不能为空!' }]" v-show="!user.id"
        >
          <a-input v-model:value="user.password"  />
        </a-form-item>

      </a-form>
    </a-modal>

    <a-modal
        title="重置密码"
        v-model:open="resetModalVisible"
        @ok="handleResetModalOk"
    >

      <a-form :model="user" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="新密码">
          <a-input v-model:value="user.password"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>


</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
declare let hexMd5: any;
declare let KEY: any;
export default defineComponent({





  name: 'AdminUser',
  setup() {


    const users = ref();
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });

    const columns = [
      {
        title: '登陆名',
        dataIndex: 'loginName'
      },

      {
        title: '昵称',
        dataIndex: 'name'
      },
      {
        title: '密码',
        dataIndex: 'password'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * 表单
      */
    const loading = ref(false);

    const user = ref()

    const param = ref()
    param.value = {};

    const open = ref<boolean>(false);

    const resetModalVisible = ref(false);



    const modalLoading = ref(false);
    const handleModelOk = (e: MouseEvent) => {
      console.log(e);
      user.value.password = hexMd5(user.value.password + KEY)
      axios.post("/user/save", user.value
        ).then((response) => {
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          open.value=false;
          modalLoading.value = true;
          handleQuery({

            page:pagination.value.current,
            size: pagination.value.pageSize
          })
        }else {
          message.error(data.message);
        }
      });

    }
    /**
     * 重置密码
     */
    const handleResetModalOk = () => {


      resetModalVisible.value = true;
      user.value.password = hexMd5(user.value.password + KEY)
      axios.post("/user/reset-password", user.value).then((response) => {

        const data = response.data; // data = commonResp
        if (data.success) {
          resetModalVisible.value = false;

          // 重新加载列表
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);
        }
      });
    };
    /**
     * 重置密码
     */

    const resetPassword = (record: any) => {
      console.log("222222",record)
      user.value = Tool.copy(record);
      resetModalVisible.value = true;
    };

    /**
     * 编辑
     */

    const edit = (record: any) => {
      console.log("222222",record)
      user.value = Tool.copy(record);
      open.value = true;
    };
    /**
     * 新增
     * @param record
     */
    const add = () => {

      open.value = true;
      user.value = {};
    };
    /**
     * 数据查询
     **/


    const handleQuery = (params: any) => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      // ebooks.value = [];
      axios.get("/user/list", {
        params:{
          page: params.page,
          size: params.size,
          loginName: param.value.loginName
        }

      }
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          users.value = data.content.list;
          console.log(data.content.list)
          // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });

    };

    /**
     * 删除
     * @param id
     */
    const handleDelete = ( id: number)=>{
      loading.value = true;
      axios.delete("/user/delete/" + id
      ).then((response) => {
        const data = response.data;
        if (data.success) {

          handleQuery({

            page:pagination.value.current,
            size: pagination.value.pageSize

          })
        }
      });
    }

    /**
     * 表格点击页码时触发
     */
    const handleTableChange = (pagination: any) => {
      console.log("看看自带的分页参数都有啥：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

  onMounted(()=>{
    handleQuery( {
          page: pagination.value.current,
          size: pagination.value.pageSize
    }
    );
  });
    return {
      param,
      user,
      users,
      pagination,
      columns,
      loading,
      edit,
      add,
      modalLoading,
      handleResetModalOk,
      handleTableChange,
      resetModalVisible,
      handleModelOk,
      handleQuery,
      handleDelete,
      resetPassword,
      open
    }
  }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>


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
              <a-input v-model:value="param.name" placeholder="名称">
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
              :data-source="ebooks"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
          >

            <template v-slot:cover="{ text: cover }">
              <img v-if="cover" :src="cover" alt="avatar" />
            </template>

<!--            <template v-slot:category="{  record }">
              <span>{{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }}</span>
            </template>
-->
<!--             eslint-disable-next-line-->
            <template v-slot:action="{ record,text }">
              <a-space size="small">
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
             title="电子书"
             @ok="handleModelOk">

      <a-form :model="ebook" :label-col="{span : 6}" >
        <a-form-item label="封面">
          <a-input v-model:value="ebook.cover" />
        </a-form-item>
        <a-form-item label="名称"
                     :rules="[{ required: true, message: '名称不能为空!' }]"
        >
          <a-input v-model:value="ebook.name" />
        </a-form-item>
        <a-form-item label="分类一">
          <a-input v-model:value="ebook.category1Id" />
        </a-form-item>
        <a-form-item label="分类二">
          <a-input v-model:value="ebook.category2Id" />
        </a-form-item>
        <a-form-item label="描述">
          <a-input v-model:value="ebook.description" type="text"/>
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

export default defineComponent({





  name: 'AdminEbook',
  setup() {
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });

    const columns = [
      {
        title: '封面',
        dataIndex: 'cover',
        slots: { customRender: 'cover' }
      },
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '分类一',
        key: 'category1Id',
        dataIndex: 'category1Id'
      },
      {
        title: '分类二',
        key: 'category2Id',
        dataIndex: 'category2Id'
      },
      {
        title: '文档数',
        dataIndex: 'docCount'
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount'
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
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

    // const queryName = ref();
    const ebook = ref({})

    const param = ref()
    param.value = {};

    const open = ref<boolean>(false);
    const modalLoading = ref(false);
    const handleModelOk = (e: MouseEvent) => {
      console.log(e);



      axios.post("/ebook/save", ebook.value
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
     * 编辑
     */

    const edit = (record: any) => {
      ebook.value = Tool.copy(record);
      open.value = true;

    };
    /**
     * 新增
     * @param record
     */
    const add = () => {

      open.value = true;
      ebook.value = {};
    };
    /**
     * 数据查询
     **/


    const handleQuery = (params: any) => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      ebooks.value = [];
      axios.get("/ebook/list", {
        params:{
          page: params.page,
          size: params.size,
          name: param.value.name
        }

      }
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          ebooks.value = data.content.list;
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
      axios.delete("/ebook/delete/" + id
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
    handleQuery({
      page:pagination.value.current,
      size: pagination.value.pageSize
    })
  });
    return {

      param,
      ebooks,
      pagination,
      columns,
      loading,
      ebook,


      edit,
      add,

      modalLoading,
      handleTableChange,
      handleModelOk,
      handleQuery,
      handleDelete,

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


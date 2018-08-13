create table elastic_dispatcher_config (
  config_id int not null auto_increment comment '配置id',
  config_name varchar(50) comment '配置名称',
  max_value int comment '最大值',
  current_value int comment '当前值',
  primary key (config_id),
  unique key (config_name)
) comment '全局配置';

create table elastic_dispatcher_worker (
  worker_id int not null auto_increment comment '工作者id',
  worker_name varchar(100) not null comment '工作者名称',
  max_threads int not null comment '最大工作线程数',
  current_threads int not null comment '当前工作线程数',
  primary key (worker_id),
  unique key (worker_name)
) comment '工作者';

create table elastic_dispatcher_job (
  job_id int not null auto_increment comment '任务id',
  job_uuid varchar(36) not null comment '任务uuid',
  job_no varchar(128) not null comment '外部任务编号',
  worker_id int not null comment '工作者id',
  job_info varchar(1000) not null comment '任务信息',
  job_state int not null comment '任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）',
  expected_start timestamp not null default current_timestamp comment '期望开始日期',
  acceptable_start timestamp not null default current_timestamp comment '可接受的开始日期',
  expire_date timestamp not null default current_timestamp comment '超时时间',
  actual_start timestamp null default null comment '实际开始时间',
  actual_end timestamp null default null comment '实际结束时间',
  max_retry int not null comment '最大重试次数',
  retry_times int not null comment '重试次数',
  fail_reason varchar(200) not null comment '失败原因',
  create_date timestamp not null default current_timestamp comment '任务创建日期',
  primary key (job_id),
  unique key (job_uuid),
  unique key (job_no),
  key (job_state, expected_start, acceptable_start, create_date),
  key (job_state, expire_date)
) comment '任务';

insert elastic_dispatcher_config (config_name,max_value,current_value) values ('threads', 100, 0);
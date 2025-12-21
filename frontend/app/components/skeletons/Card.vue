<template>
  <UCard
    variant="soft"
    class="border border-gray-300 dark:border-gray-700 shadow-md hover:border-gray-400 transition-colors hover:shadow-md cursor-pointer"
    :ui="{
      header: 'font-semibold text-lg flex flex-col max-w-full gap-2',
      footer:
        'text-sm text-gray-400 flex flex-wrap items-center justify-between gap-x-4 gap-y-2',
      body: '!py-2',
    }"
  >
    <template #header>
      <USkeleton class="h-[1.85rem] w-[80%] bg-gray-600" />
      <USkeleton class="h-6 w-[40%] bg-gray-600" />
    </template>

    <div class="flex flex-wrap gap-2 items-center">
      <USkeleton class="h-6 w-32 bg-gray-600" />
      <USkeleton class="h-6 w-14 bg-gray-600" />
      <USkeleton class="h-6 w-18 bg-gray-600" />
      <USkeleton class="h-6 w-16 bg-gray-600" />
    </div>

    <template #footer>
      <USkeleton class="h-6 w-16 bg-gray-600" />
      <USkeleton class="h-6 w-32 bg-gray-600" />
    </template>
  </UCard>
</template>

<template>
  <div class="flex flex-col md:flex-row gap-6">
    <!-------- SMALL SCREEN ---------->
    <LayoutSettingsMenuSM />
    <section class="pt-12 md:hidden">
      <slot />
    </section>

    <!---------- MD+ ---------->
    <LayoutSettingsMenuMD />
    <section class="hidden md:block">
      <slot />
    </section>
  </div>
</template>
